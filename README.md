# gaggle-mean-median-service

This repo contains a Lambda function that calculates the mean and median values of a series of numbers. It also attaches that Lambda to an AWS API Gateway instance.

This Lambda function was set up with [Spring Cloud Function](https://spring.io/projects/spring-cloud-function). This makes it easy to build, deploy, maintain, and iterate on.

## Environment Set Up

This project uses Java 17 Corretto. Use this version for parity with the AWS Lambda runtime.

You can use the included Maven wrapper to run commands, such as `./mvnw clean install`.

## How to Build

You can build the jar file with the following command.

```bash
./mvnw install
```

This will create a few jars in the `./target` directory. We'll be uploading the `*-aws.jar` file into AWS Lambda.

## How to Deploy

### Automated Deployment

Commits merged into the `main` branch automatically trigger the Github Actions CI/CD pipeline to deploy to AWS. No further action is required.

If you're setting up this repo for the first time, make sure to add the following Github secrets. They are used in the CI/CD pipeline.

- AWS_ACCESS_KEY_ID
- AWS_SECRET_ACCESS_KEY
- AWS_REGION

### Manual Deployment

First, build the jar with `./mvnw install`.

From there, `cd` into the `./infrastructure` directory and follow [the instructions](./infrastructure/README.md) for deploying to AWS using the CDK.

## Testing

You can either invoke the Lambda from the AWS console with an API Gateway proxy event, or hit the live URL below.

```bash
curl -XPOST 'https://jl8fxuewvl.execute-api.us-west-2.amazonaws.com/prod/mean-median' \
    --header 'Content-Type: application/json' \
    --data '{ "values": [1, 2, 2, 3] }'
```

### Example Request

```bash
curl -XPOST '<api-url>/mean-median' \
    --header 'Content-Type: application/json' \
    --data '{ "values": [1, 2, 2, 3] }'
```

### Example Response

```json
{
    "mean": 2.0,
    "median": 2.0
}
```
