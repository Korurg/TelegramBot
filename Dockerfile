FROM gradle:8.10.2-jdk21-alpine AS build

WORKDIR /build

COPY ./scripts ./scripts
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./gradle.properties ./gradle.properties
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./CoopChat ./CoopChat
COPY ./TelegramBotCoreImpl ./TelegramBotCoreImpl
COPY ./TelegramBotCoreApi ./TelegramBotCoreApi
COPY ./TelegramBot ./TelegramBot


RUN gradle :TelegramBot:build -x test
RUN gradle :buildAndCopyModules -x test

FROM eclipse-temurin:21-alpine

WORKDIR /telegram-bot

COPY --from=build /build/TelegramBot/build/libs/*.jar ./telegram-bot.jar
COPY --from=build /build/scripts/entrypoint.sh ./entrypoint.sh
COPY --from=build /build/extensions/* ./extensions/

RUN chmod ug+x ./entrypoint.sh

ENTRYPOINT ["/bin/sh", "-c", "./entrypoint.sh"]
