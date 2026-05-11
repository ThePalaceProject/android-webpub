#!/bin/sh

protoc \
--java_out=org.thepalaceproject.webpub.cmdline/src/main/kotlin/ \
DBSerialization.proto
