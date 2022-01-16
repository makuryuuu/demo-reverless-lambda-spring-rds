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
}