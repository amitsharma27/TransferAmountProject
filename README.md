# TransferAmountProject

Fund Transfer Between accounts Implemented Project for Fund Transfer Between 2 Account

Improvements 1.Can Implement Swagger for better upderstanding of Api 2.Can Add transactionId for Each Transaction 3.Account No starting digit can we marked for Printing in Logs 4.Caching Can be used based on Requirements 5.Logger part Can we improved Based on More Scenerios

Added Some Scenerio Responses

Imput { "fromAccount":123, "toAccount":456, "amount":1000,

}

Output Success { "message": "Success", "statusCode": 200 } --Failures

{ "timestamp": "13-08-2022 06:36:19", "statusCode": 1001, "status": "Failed", "message": "Incorrect Amount Entered " } { "timestamp": "13-08-2022 06:36:19", "statusCode": 1001, "status": "Failed", "message": "Can not Transfer fund ,SomeThing Went Wrong" }

{ "timestamp": "13-08-2022 06:44:52", "statusCode": 1001, "status": "Failed", "message": "insufficient Balance or Failed to Get Account Details" }

{ "timestamp": "13-08-2022 06:47:42", "statusCode": 1001, "status": "Failed", "message": " Failed to Get Account Details" }

Notification Message ending notification to owner of AccountNo 34566 ,Amount 1000
