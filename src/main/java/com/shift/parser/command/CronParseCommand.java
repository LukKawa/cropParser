package com.shift.parser.command;

import com.shift.parser.model.ParsedCronExpression;
import com.shift.parser.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@RequiredArgsConstructor
@Component
@Command(name = "cropParser", mixinStandardHelpOptions = true, version = "1.0",
        description = "Commandline to process cron expression")
public class CronParseCommand implements CommandLineRunner, Runnable {

    @Value("${parser.column}")
    private Integer columnSize = 14;

    private final ParserService parserService;

    @Parameters(index = "0", description = "The cron expression")
    private String command;

    @Override
    public void run() {
        if (command == null) {
            System.out.println("No arguments provided.");
        }

        parseAllCronExpressions(command);
    }

    @Override
    public void run(String... args) throws Exception {
        new CommandLine(this).execute(args);
    }

    public void parseAllCronExpressions(String cronExpression) {
        parserService.parseAllCronExpressions(cronExpression).stream().forEach(parsedCronExpression -> {
            String column = getParsedColumnResult(parsedCronExpression);
            System.out.println(column);
        });
    }

    private String getParsedColumnResult(ParsedCronExpression parsedCronExpression) {
        StringBuilder columnName = new StringBuilder(parsedCronExpression.getCronExpressionType().getName());
        columnName.append(" ".repeat(Math.max(0, columnSize - columnName.length())));
        return columnName + parsedCronExpression.getValue();
    }
}
