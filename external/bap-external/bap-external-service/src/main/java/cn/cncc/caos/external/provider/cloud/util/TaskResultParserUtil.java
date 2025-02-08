package cn.cncc.caos.external.provider.cloud.util;

import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.external.provider.cloud.enums.ResultParseTypeEnum;
import cn.cncc.caos.external.provider.cloud.model.FieldParseResult;
import cn.cncc.caos.external.provider.cloud.model.FieldParseRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: TaskResultParserUtil
 * @Description: 云平台状态检查任务结果解析工具
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/26 15:24
 */
@Slf4j
@Component
public class TaskResultParserUtil {
    public List<FieldParseResult> ParseHttpResult(String taskInstId, String httpResult, String parseRule) throws IOException {
        List<FieldParseResult> fieldParseResultList = new ArrayList<>();
        List<FieldParseRule> fieldParseRuleList = JacksonUtil.jsonToObj(parseRule, List.class, FieldParseRule.class);
        for (FieldParseRule fieldParseRule : fieldParseRuleList){
            if (!fieldParseRule.isValid()){
                log.warn("Task result parse rule is not valid for task inst = {}",taskInstId);
                continue;
            }
            FieldParseResult fieldParseResult = new FieldParseResult();
            fieldParseResult.setbSuccess(true);
            String exprType = fieldParseRule.getExprType();
            ResultParseTypeEnum parseTypeEnum = ResultParseTypeEnum.getNameByIndex(Integer.valueOf(exprType));
            String fieldNameExpr = fieldParseRule.getFieldExpr();
            DocumentContext documentContext = JsonPath.parse(httpResult);
            switch (parseTypeEnum){
                //直接提取字段值
                case EXTRACT_VALUE:
                    Object fieldV = documentContext.read(fieldNameExpr);
                    String value = String.valueOf(fieldV);
                    fieldParseResult.setFieldValue(value);
                    break;
                //字段求和
                case EXTRACT_SUM:
                    List<Double> values = documentContext.read(fieldNameExpr);
                    double sumResult = values.stream().mapToDouble((s)->s).sum();
                    fieldParseResult.setFieldValue(String.valueOf(sumResult));
                    break;
                //字段计数
                case EXTRACT_COUNT:
                    List<Object> objects = documentContext.read(fieldNameExpr);
                    fieldParseResult.setFieldValue(String.valueOf(objects.size()));
                    break;
                //正则匹配
                case EXTRACT_EXPR:
                    Pattern pattern = Pattern.compile(fieldNameExpr);
                    Matcher matcher = pattern.matcher(httpResult);
                    if (matcher.find()) {
                        fieldParseResult.setFieldValue(matcher.group());
                    }else{
                        fieldParseResult.setbSuccess(false);
                    }
                    break;
                default:
                    fieldParseResult.setbSuccess(false);
                    log.warn("Invalid result parser type :{}",exprType);
            }

            fieldParseResult.setFieldName(fieldParseRule.getFieldName());
            fieldParseResult.setExpect(fieldParseRule.getExpect());
            fieldParseResultList.add(fieldParseResult);
        }
        return fieldParseResultList;
    }
}
