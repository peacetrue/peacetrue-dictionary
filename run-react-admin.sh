#!/bin/bash

git clone -b "1.0.0" https://github.com/peacetrue/peacetrue-application
cd peacetrue-application/peacetrue-application-reactadmin || exit
pnpm install ra-dictionary@0.0.0-3

pnpm start
