using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;

namespace SAPLibrary
{
        public class Inventory
    {
        public async Task<object> Invoke(object input)
        {
            // Edge marshalls data to .NET using an IDictionary<string, object>
            var payload = (IDictionary<string, object>) input;
            var Group = (string) payload["Group"];
            var Whs = (string) payload["Whs"];
            return await QueryStock(Group, Whs);
        }

        public async Task<List<SampleItem>> QueryStock(string Group, string Whs)
        {
            // Use the same connection string env variable
            var connectionString = Environment.GetEnvironmentVariable("EDGE_SQL_CONNECTION_STRING");
            if (connectionString == null)
                throw new ArgumentException("You must set the EDGE_SQL_CONNECTION_STRING environment variable.");

            // Paging the result set using a common table expression (CTE).
            // You may rather do this in a stored procedure or use an 
            // ORM that supports async.
            var sql = @"
                            SELECT 
                                T0.ItemCode, T0.WhsCode, T0.OnHand, 
                                T1.AvgPrice, T0.OnHand * T1.AvgPrice as [Value]
                            FROM 
                                OITW T0
                            inner join 
                                OITM T1 on T0.ItemCode = T1.ItemCode
                            where 
                                T0.itemcode like @Group and T0.WhsCode = @Whs and T0.onHand > 0;
                    ";
            var items = new List<SampleItem>();

            using (var cnx = new SqlConnection(connectionString))
            {
                using (var cmd = new SqlCommand(sql, cnx))
                {
                    await cnx.OpenAsync();
                    // https://msdn.microsoft.com/en-us/library/system.data.sqldbtype(v=vs.110).aspx
                    cmd.Parameters.Add(new SqlParameter("@Group", SqlDbType.NVarChar) { Value = Group });
                    cmd.Parameters.Add(new SqlParameter("@Whs", SqlDbType.NVarChar) { Value = Whs });

                    using (var reader = await cmd.ExecuteReaderAsync(CommandBehavior.CloseConnection))
                    {
                        while (await reader.ReadAsync())
                        {
                            var item = new SampleItem
                            {
                                ItemCode = reader.GetString(0), 
                                WhsCode  = reader.GetString(1), 
                                OnHand   = reader.GetDecimal(2).ToString ("#.###"),  // for rounding 
                                AvgPrice = reader.GetDecimal(3).ToString ("#.###"), 
                                Value    = reader.GetDecimal(4).ToString ("#.###")
                            };
                           items.Add(item);
                        }
                    }
                }
            }
            return items;
        } 
    }

    public class SampleItem
    {
        public string ItemCode { get; set; }
        public string WhsCode { get; set; }
        public string OnHand { get; set; }
        public string AvgPrice { get; set; }
        public string Value { get; set; }
    }
}