package com.shift.parser.service;

import com.shift.parser.model.CronExpression;
import com.shift.parser.model.CronExpressionTypeEnum;
import com.shift.parser.model.CronOperatorType;
import com.shift.parser.model.ParsedCronExpression;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParserService {


    public List<ParsedCronExpression> parseAllCronExpressions(String cronExpression) {
        validateCronExpression();
        String[] expressions = cronExpression.split(" ");
        return Arrays.stream(CronExpressionTypeEnum.values()).map(type -> {
            String expression = expressions[type.getPosition()];
            String result = parseCronExpression(CronExpression.builder()
                    .cronExpressionType(type)
                    .value(expression)
                    .build());

            return ParsedCronExpression.builder()
                    .cronExpressionType(type)
                    .value(result)
                    .build();
        }).toList();
    }

    //@todo implement validation on cron expression
    private void validateCronExpression() {

    }

    public String parseCronExpression(CronExpression cronExpression) {
        List<String> result = new ArrayList<>();
        if(cronExpression.getValue().contains(CronOperatorType.ALL.getOperator())) {
            result = parseEveryOperator(cronExpression);
        } else if(cronExpression.getValue().contains(CronOperatorType.BETWEEN.getOperator())) {
            result = parseBetweenOperator(cronExpression);
        } else if(cronExpression.getValue().contains(CronOperatorType.COMA.getOperator())) {
            result = parseComaOperator(cronExpression);
        } else {
            result.add(cronExpression.getValue());
        }

        return String.join(" ", result);
    }

    private List<String> parseComaOperator(CronExpression cronExpression) {
        return Arrays.stream(cronExpression.getValue().split(","))
                .filter(s -> Integer.parseInt(s) >= cronExpression.getCronExpressionType().getMinValue()
                        && Integer.parseInt(s) <= cronExpression.getCronExpressionType().getMaxValue())
                .toList();
    }

    private List<String>  parseBetweenOperator(CronExpression cronExpression) {
        List<String> result = new ArrayList<>();
        String[] strings = cronExpression.getValue().split("-");

        for(int i = Math.max(cronExpression.getCronExpressionType().getMinValue(), Integer.parseInt(strings[0]));
            i <= Math.min(cronExpression.getCronExpressionType().getMaxValue(), Integer.parseInt(strings[1])); i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }

    private List<String> parseEveryOperator(CronExpression cronExpression) {
        List<String> result = new ArrayList<>();
        int increment = 1;
        if(cronExpression.getValue().contains("/")) {
            increment = Integer.parseInt(cronExpression.getValue().split("/")[1]);
        }
        for(int i = cronExpression.getCronExpressionType().getMinValue();
            i <= cronExpression.getCronExpressionType().getMaxValue(); i += increment) {
            result.add(String.valueOf(i));
        }
        return result;
    }
}
