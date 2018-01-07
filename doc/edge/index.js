
var hello = require('edge').func('mysample.cs');
console.log('Hi from JS');
hello(null,true)

/*
var hello = require('edge').func({
    assemblyFile: 'My.Edge.Samples.dll',
    typeName: 'Samples.FooBar.MyType',
    methodName: 'MyMethod' // Func<object,Task<object>>
}});

hello('Node.js', function (error, result) { ... });
*/

/*
var accessSql = edge.func(function () {`
    #r "System.Data.dll"

    using System.Data;
    
    async (input) => {
        ...
    }
`});
*/