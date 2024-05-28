package com.shift.parser.service;

import com.shift.parser.model.CronExpression;
import com.shift.parser.model.CronExpressionTypeEnum;
import com.shift.parser.model.ParsedCronExpression;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserServiceTest {

    private static final ParserService parserService = new ParserService();

    @Test
    public void testSimpleExpression() {
        //given
        CronExpression cronExpression = new CronExpression("1", CronExpressionTypeEnum.HOUR);
        //when
        String result = parserService.parseCronExpression(cronExpression);
        //then
        assertEquals(result, "1");
    }

    @Test
    public void testParseAllExpression() {
        //given
        CronExpression cronExpression = new CronExpression("*", CronExpressionTypeEnum.HOUR);
        //when
        String result = parserService.parseCronExpression(cronExpression);
        //then
        assertEquals(result, "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23");
    }

    @Test
    public void testParsePartExpression() {
        //given
        CronExpression cronExpression = new CronExpression("*/3", CronExpressionTypeEnum.HOUR);
        //when
        String result = parserService.parseCronExpression(cronExpression);
        //then
        assertEquals(result, "0 3 6 9 12 15 18 21");
    }

    @Test
    public void testParseBetweenExpression() {
        //given
        CronExpression cronExpression = new CronExpression("2-5", CronExpressionTypeEnum.HOUR);
        //when
        String result = parserService.parseCronExpression(cronExpression);
        //then
        assertEquals(result, "2 3 4 5");
    }

    @Test
    public void testParseComaExpression() {
        //given
        CronExpression cronExpression = new CronExpression("2,5", CronExpressionTypeEnum.HOUR);
        //when
        String result = parserService.parseCronExpression(cronExpression);
        //then
        assertEquals(result, "2 5");
    }

    @Test
    public void testParseExpression() {
        //when
        List<ParsedCronExpression> strings = parserService.parseAllCronExpressions("*/15 0 1,15 * 1-5 /usr/bin/find");
        //then
        assertEquals(strings.get(0).getValue(), "0 15 30 45");
        assertEquals(strings.get(0).getCronExpressionType(), CronExpressionTypeEnum.MINUTES);
        assertEquals(strings.get(1).getValue(), "0");
        assertEquals(strings.get(1).getCronExpressionType(), CronExpressionTypeEnum.HOUR);
        assertEquals(strings.get(2).getValue(), "1 15");
        assertEquals(strings.get(2).getCronExpressionType(), CronExpressionTypeEnum.DAY_OF_MONTH);
        assertEquals(strings.get(3).getValue(), "1 2 3 4 5 6 7 8 9 10 11 12");
        assertEquals(strings.get(3).getCronExpressionType(), CronExpressionTypeEnum.MONTH);
        assertEquals(strings.get(4).getValue(), "1 2 3 4 5");
        assertEquals(strings.get(4).getCronExpressionType(), CronExpressionTypeEnum.DAY_OF_WEEK);
        assertEquals(strings.get(5).getValue(), "/usr/bin/find");
        assertEquals(strings.get(5).getCronExpressionType(), CronExpressionTypeEnum.COMMAND);
    }
}
