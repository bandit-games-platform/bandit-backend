#!/bin/bash

docker build \
--platform=linux/arm64 \
-t bdev42/bandit-games:be-closet \
. \
&& docker image save bdev42/bandit-games:be-closet -o build-be-closet.tar \
&& scp build-be-closet.tar pi:~/bandit
