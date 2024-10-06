#!/bin/sh
if [ "$ALGORITHM_TYPE" = "basic" ]; then
    java -Dreleases.file.name=/app/releases.txt \
         -Dsprint.duration=$SPRINT_DURATION \
         -Doutput.file.name=/app/output/output.txt \
         -Doutput.file.name.advanced=/app/output/output-advanced.txt \
         -jar basic.jar;
elif [ "$ALGORITHM_TYPE" = "advanced" ]; then
    java -Dreleases.file.name=/app/releases.txt \
         -Dsprint.duration=$SPRINT_DURATION \
         -Doutput.file.name=/app/output/output.txt \
         -Doutput.file.name.advanced=/app/output/output-advanced.txt \
         -jar advanced.jar;
else
    echo 'Invalid ALGORITHM_TYPE. Use basic or advanced.' && exit 1;
fi