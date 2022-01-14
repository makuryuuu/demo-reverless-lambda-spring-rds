package org.thuanthm.lambdaRDSsample1.utils;

import com.amazon.rdsdata.client.Executor;
import com.amazon.rdsdata.client.RdsDataClient;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.BeginTransactionRequest;
import com.amazonaws.services.rdsdata.model.BeginTransactionResult;
import com.amazonaws.services.rdsdata.model.CommitTransactionRequest;
import com.amazonaws.services.rdsdata.model.CommitTransactionResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RDSUtils {

    @Value("${custom.config.aws.region}")
    private String region;

    @Value("${custom.config.aws.accesskey}")
    private String accesskey;

    @Value("${custom.config.aws.secretkey}")
    private String secretkey;

    @Value("${custom.config.aws.rds.resource.serverless.arn}")
    private String dbresourceARN;

    @Value("${custom.config.aws.rds.secret}")
    private String dbsecretARN;

    @Value("${custom.config.aws.rds.resource.serverless.dbname}")
    public String dbname;
//    private static String RESOURCE_ARN = "arn:aws:rds:ap-southeast-1:593863423728:cluster:auroralab-postgres-serverless-cluster";
//    private static String SECRET_ARN = "arn:aws:secretsmanager:ap-southeast-1:593863423728:secret:aurora-postgres-serverless-secret-Ns1yn8";
//    public static String DBNAME = "aurora_postgres_db";

    public AWSRDSData awsRDSDataService(){
        return AWSRDSDataClient.builder()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey)))
            .build();
    }

    public Executor executor(String query){
        return RdsDataClient.builder()
                .rdsDataService(awsRDSDataService())
                .resourceArn(dbresourceARN)
                .secretArn(dbsecretARN)
                .database(dbname)
                .build().forSql(query);
    }

    public ExecuteStatementRequest executeStatementRequest(String query){
        return new ExecuteStatementRequest()
                .withResourceArn(dbresourceARN)
                .withSecretArn(dbsecretARN)
                .withDatabase(dbname)
                .withSql(query);
    }

    public ExecuteStatementRequest executeStatementRequest(String query, String transactionId){
        return new ExecuteStatementRequest()
                .withResourceArn(dbresourceARN)
                .withSecretArn(dbsecretARN)
                .withDatabase(dbname)
                .withTransactionId(transactionId)
                .withSql(query);
    }

    public String beginTransaction() {
        BeginTransactionRequest beginTransactionRequest = new BeginTransactionRequest()
                .withResourceArn(dbresourceARN)
                .withSecretArn(dbsecretARN)
                .withDatabase(dbname);

        BeginTransactionResult beginTransactionResult = awsRDSDataService().beginTransaction(beginTransactionRequest);
        return beginTransactionResult.getTransactionId();
    }

    public CommitTransactionResult commitTransaction(String transactionId) {
        CommitTransactionRequest commitTransactionRequest = new CommitTransactionRequest()
                .withTransactionId(transactionId)
                .withResourceArn(dbresourceARN)
                .withSecretArn(dbsecretARN);
        return awsRDSDataService().commitTransaction(commitTransactionRequest);
    }

//    public void fetchData(String query, JsonObject params, Class objclass) {
//        Executor rdsDataClientExecutor = RdsDataClient.builder()
//                .rdsDataService(
//                        AWSRDSDataClient.builder()
//                                .withRegion("ap-southeast-1")
//                                .withCredentials(
//                                        new AWSStaticCredentialsProvider(
//                                                new BasicAWSCredentials("AKIAYURIEW3YNH34QTZZ",
//                                                        "bJu2vASHaPIsi4W84hJHmo4qI3jXKgTJNOI8hohI")))
//                                .build())
//                .resourceArn(RESOURCE_ARN)
//                .secretArn(SECRET_ARN)
//                .database(DBNAME)
//                .build().forSql(query);
//        params.entrySet().forEach(entry->{
//            rdsDataClientExecutor.withParameter(entry.getKey(), entry.getValue().getAsInt());
//        });
//        rdsDataClientExecutor.execute().mapToList(objclass).forEach(e -> System.out.println(new Gson().toJson(e)));
//        AWSRDSData rdsData = AWSRDSDataClient.builder().build();
//
//        ExecuteStatementRequest request = new ExecuteStatementRequest()
//                .withResourceArn(RESOURCE_ARN)
//                .withSecretArn(SECRET_ARN)
//                .withDatabase(DBNAME)
//                .withSql(query);
//
//        ExecuteStatementResult result = rdsData.executeStatement(request);
//
//        for (List<Field> fields: result.getRecords()) {
//            String stringValue = fields.get(0).getStringValue();
//            long numberValue = fields.get(1).getLongValue();
//
//            System.out.println(String.format("Fetched row: string = %s, number = %d", stringValue, numberValue));
//        }
//    }

//    public void commitTransaction(String query) {
//        RdsDataClient rdsDataClient = RdsDataClient.builder()
//                .resourceArn(RESOURCE_ARN)
//                .secretArn(SECRET_ARN)
//                .database(DBNAME)
//                .build();
//        Executor executor = rdsDataClient.forSql(query);
//        String transactionId = rdsDataClient.beginTransaction();
//        rdsDataClient.commitTransaction(transactionId);
//
//        params.entrySet().forEach(entry->{
//            rdsDataClientExecutor.withParameter(entry.getKey(), entry.getValue());
//        });

//        AWSRDSData rdsData = AWSRDSDataClient.builder()
//                .withRegion("ap-southeast-1")
//                .withCredentials(new AWSStaticCredentialsProvider(
//                        new BasicAWSCredentials("AKIAYURIEW3YNH34QTZZ","bJu2vASHaPIsi4W84hJHmo4qI3jXKgTJNOI8hohI")))
//                .build();
//
//        BeginTransactionRequest beginTransactionRequest = new BeginTransactionRequest()
//                .withResourceArn(RESOURCE_ARN)
//                .withSecretArn(SECRET_ARN)
//                .withDatabase(DBNAME);
//
//        BeginTransactionResult beginTransactionResult = rdsData.beginTransaction(beginTransactionRequest);
//        String transactionId = beginTransactionResult.getTransactionId();
//
//        ExecuteStatementRequest executeStatementRequest = new ExecuteStatementRequest()
//                .withTransactionId(transactionId)
//                .withResourceArn(RESOURCE_ARN)
//                .withSecretArn(SECRET_ARN)
//                .withSql(query);
//        rdsData.executeStatement(executeStatementRequest);
//
//        CommitTransactionRequest commitTransactionRequest = new CommitTransactionRequest()
//                .withTransactionId(transactionId)
//                .withResourceArn(RESOURCE_ARN)
//                .withSecretArn(SECRET_ARN);
//        rdsData.commitTransaction(commitTransactionRequest);
//    }
}