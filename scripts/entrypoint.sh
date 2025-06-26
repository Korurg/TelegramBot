#!/usr/bin/env /bin/sh

EXTENSIONS_PATH="${TELEGRAM_BOT_EXTENSIONS_PATH:-./extensions}"
java -jar ./telegram-bot.jar --bot.extensions="$EXTENSIONS_PATH"
