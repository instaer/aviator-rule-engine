package com.github.instaer.ruleengine.condition;

import com.github.instaer.ruleengine.constants.ConditionRelationType;
import com.github.instaer.ruleengine.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.instaer.ruleengine.constants.ConditionRelationType.*;

@Component
public class IntervalCondition implements Condition {
    private final static Pattern pattern = Pattern.compile("([\\[(])([^,]+),([^,]+)([])])");

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                INTERVAL_NUMBER,
                INTERVAL_STRING_LENGTH
        };
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            String referenceValue = conditionInstance.getReferenceValue();
            String[] intervalBounds = parseIntervalBounds(referenceValue);
            Assert.notNull(intervalBounds, "interval expression malformed:" + referenceValue);

            return String.format(format(conditionInstance), conditionInstance.getVariableName(), intervalBounds[0], intervalBounds[1], intervalBounds[2], intervalBounds[3]);
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }

    private String[] parseIntervalBounds(String interval) {
        Matcher matcher = pattern.matcher(interval);

        if (matcher.find()) {
            String leftBracket = matcher.group(1);
            String lowerBound = matcher.group(2);
            String upperBound = matcher.group(3);
            String rightBracket = matcher.group(4);

            boolean isLeftInclusive = leftBracket.equals("[");
            boolean isRightInclusive = rightBracket.equals("]");

            String lowerComparisonOperator = isLeftInclusive ? ">=" : ">";
            String upperComparisonOperator = isRightInclusive ? "<=" : "<";

            return new String[]{lowerComparisonOperator, lowerBound, upperComparisonOperator, upperBound};
        }

        return null;
    }
}