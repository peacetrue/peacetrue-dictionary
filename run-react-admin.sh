#!/bin/bash

git clone -b "1.0.0" https://github.com/peacetrue/peacetrue-application
cd peacetrue-application/peacetrue-application-react-admin || exit
pnpm install dictionary-react-admin@0.0.0-1

pnpm start
