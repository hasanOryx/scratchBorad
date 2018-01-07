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
            var pageNumber = (int) payload["pageNumber"];
            var pageSize = (int) payload["pageSize"];
            return await QueryStock(pageNumber, pageSize);
        }

        public async Task<List<SampleUser>> QueryStock(int pageNumber, int pageSize)
        {
            // Use the same connection string env variable
            var connectionString = Environment.GetEnvironmentVariable("EDGE_SQL_CONNECTION_STRING");
            if (connectionString == null)
                throw new ArgumentException("You must set the EDGE_SQL_CONNECTION_STRING environment variable.");

            // Paging the result set using a common table expression (CTE).
            // You may rather do this in a stored procedure or use an 
            // ORM that supports async.
            var sql = @"
                    DECLARE @RowStart int, @RowEnd int;
                    SET @RowStart = (@PageNumber - 1) * @PageSize + 1;
                    SET @RowEnd = @PageNumber * @PageSize;

                    WITH Paging AS
                    (
                            SELECT 
							ROW_NUMBER() OVER (ORDER BY T0.ItemCode DESC) AS RowNum,
                            T0.ItemCode, T0.WhsCode, T0.OnHand, 
                            T1.AvgPrice, T0.OnHand * T1.AvgPrice as [Value]
                            FROM 
                            OITW T0
                            inner join OITM T1 on T0.ItemCode = T1.ItemCode
                            where T0.itemcode like 'RM%' and T0.WhsCode ='RM' and T0.onHand > 0
                    )
                    SELECT  ItemCode, WhsCode, OnHand, AvgPrice, Value
                    FROM    Paging
                    WHERE   RowNum BETWEEN @RowStart AND @RowEnd
                    ORDER BY RowNum;
            ";
            var items = new List<SampleItem>();

            using (var cnx = new SqlConnection(connectionString))
            {
                using (var cmd = new SqlCommand(sql, cnx))
                {
                    await cnx.OpenAsync();

                    cmd.Parameters.Add(new SqlParameter("@PageNumber", SqlDbType.Int) { Value = pageNumber });
                    cmd.Parameters.Add(new SqlParameter("@PageSize", SqlDbType.Int) { Value = pageSize });

                    using (var reader = await cmd.ExecuteReaderAsync(CommandBehavior.CloseConnection))
                    {
                        while (await reader.ReadAsync())
                        {
                            var item = new SampleItem
                            {
                                ItemCode = reader.GetInt32(0), 
                                WhsCode  = reader.GetString(1), 
                                OnHand   = reader.GetString(2), 
                                AvgPrice = reader.GetString(3), 
                                Value    = reader.GetDateTime(4)
                            };
                           items.Add(item);
                        }
                    }
                }
            }
            return users;
        } 
    }

    public class SampleUser
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public DateTime CreateDate { get; set; }
    }
}