package org.thuanthm.lambdaRDSsample1.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class ProxyHandler extends SpringBootRequestHandler<APIGatewayProxyRequestEvent, Object> {
}
