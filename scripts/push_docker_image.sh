#!/usr/bin/env /bin/sh

DOCKER_FILE_PATH="${DOCKER_FILE_PATH:-../}"
cd "$DOCKER_FILE_PATH" || exit
docker build -t korurg/coop-telegram-bot .
docker push korurg/coop-telegram-bot
