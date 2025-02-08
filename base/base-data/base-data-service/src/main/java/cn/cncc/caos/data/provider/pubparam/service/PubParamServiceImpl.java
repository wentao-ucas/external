package cn.cncc.caos.data.provider.pubparam.service;

import cn.cncc.caos.data.client.pubparam.api.IPubParamService;
import cn.cncc.caos.data.client.pubparam.dto.PubParamDto;
import cn.cncc.caos.data.provider.pubparam.db.dao.PubParamDynamicSqlSupport;
import cn.cncc.caos.data.provider.pubparam.db.dao.PubParamMapper;
import cn.cncc.caos.data.provider.pubparam.db.pojo.PubParam;
import cn.cncc.caos.common.core.enums.PubParamTypeEnum;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

/**
 * @className: PubParamService
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/9 9:50
 */
@Service
@Slf4j
@MojitoSchema(schemaId = "inner_api/pubParamService")
public class PubParamServiceImpl implements IPubParamService {

    @Autowired
    private PubParamMapper pubParamMapper;

    private PubParamDto convertPubParamToDto(PubParam pubParam){
        PubParamDto pubParamDto = new PubParamDto();
        BeanUtils.copyProperties(pubParam, pubParamDto);
        return pubParamDto;
    }
    private List<PubParamDto> convertPubParamToDtoList(List<PubParam> pubParamList){
        List<PubParamDto> pubParamDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pubParamList)){
            for (PubParam pubParam : pubParamList){
                pubParamDtoList.add(convertPubParamToDto(pubParam));
            }
        }
        return pubParamDtoList;
    }

    @Override
    public PubParamDto getParamDetailById(String paramId) {
        PubParam pubParam = pubParamMapper.selectByPrimaryKey(paramId);
        if (pubParam != null){
            return convertPubParamToDto(pubParam);
        }
        return null;
    }

    @Override
    public List<PubParamDto> getParamlistByType(String paramType) {
        List<PubParam> pubParamList = pubParamMapper.selectByExample()
                .where(PubParamDynamicSqlSupport.type,isEqualTo(paramType)).build().execute();
        return convertPubParamToDtoList(pubParamList);
    }
}
