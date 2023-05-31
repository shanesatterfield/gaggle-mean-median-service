# gaggle-mean-median-service

This repo contains a Lambda function that calculates the mean and median values of a series of numbers. It also attaches that Lambda to an AWs API Gateway instance.

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

### Manual Deployment

First, build the jar with `./mvnw clean install`.

From there, `cd` into the `./infrastructure` directory and follow the instructions for deploying to AWS using the CDK.
