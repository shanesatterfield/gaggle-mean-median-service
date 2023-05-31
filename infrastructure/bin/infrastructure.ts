#!/usr/bin/env node
import "source-map-support/register";
import * as cdk from "aws-cdk-lib";
import { GaggleMeanMedianServiceStack } from "../lib/gaggle-mean-median-service-stack";

const app = new cdk.App();
new GaggleMeanMedianServiceStack(app, "GaggleMeanMedianServiceStack", {
  env: {
    account: process.env.CDK_DEFAULT_ACCOUNT,
    region: process.env.CDK_DEFAULT_REGION,
  },
});
