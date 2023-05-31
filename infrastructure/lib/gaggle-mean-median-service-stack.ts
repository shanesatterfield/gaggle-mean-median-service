import * as cdk from "aws-cdk-lib";
import { Construct } from "constructs";
import * as lambda from "aws-cdk-lib/aws-lambda";
import * as apigateway from "aws-cdk-lib/aws-apigateway";

export class GaggleMeanMedianServiceStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const restApi = apigateway.RestApi.fromRestApiAttributes(
      this,
      "GaggleTechApi",
      {
        restApiId: cdk.Fn.importValue("GaggleTechRestApiId"),
        rootResourceId: cdk.Fn.importValue("GaggleTechRestApiRootResourceId"),
      }
    );

    const meanMedianLambda = new lambda.Function(
      this,
      "GaggleMeanMedianServiceLambda",
      {
        runtime: lambda.Runtime.JAVA_17,
        handler:
          "org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest",
        environment: {
          spring_cloud_function_definition: "meanMedianHandler",
        },
        code: lambda.Code.fromAsset(
          "../target/gaggle-mean-median-service-0.0.1-SNAPSHOT-aws.jar"
        ),
        timeout: cdk.Duration.seconds(10),
        memorySize: 256,
      }
    );

    restApi.root
      .addResource("mean-median")
      .addMethod("POST", new apigateway.LambdaIntegration(meanMedianLambda));
  }
}
