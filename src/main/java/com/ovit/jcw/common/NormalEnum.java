package com.ovit.jcw.common;

import com.fasterxml.jackson.annotation.*;

public class NormalEnum
{
    public enum OutType
    {
        MHTL(1, "民航铁路"), 
        ZS(2, "住宿");
        
        private int code;
        private String value;
        
        private OutType(final int code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public int getCode() {
            return this.code;
        }
        
        public void setCode(final int code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum BQJB
    {
        First(0, "一级标签"), 
        Second(1, "二级标签"), 
        Third(2, "三级标签");
        
        private int code;
        private String value;
        
        private BQJB(final int code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public int getCode() {
            return this.code;
        }
        
        public void setCode(final int code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum ExcelTYPE
    {
        GABY(29, "公安部云"), 
        DCB(30, "调查表");
        
        private int code;
        private final String desc;
        
        private ExcelTYPE(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum ExcelGABY
    {
        JBXX(0, "全国人口基本信息"), 
        JCXX(1, "基础信息"), 
        BJXX(2, "背景信息"), 
        HDXX(3, "活动信息");
        
        private int code;
        private final String desc;
        
        private ExcelGABY(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum ExcelDCB
    {
        QJJX(100, ""), 
        LBJX(101, ""), 
        QJLBJX(102, "");
        
        private int code;
        private final String desc;
        
        private ExcelDCB(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum FLType
    {
        BZK_GL(0, "标准库-关联"), 
        BZK_BS(1, "标准库");
        
        private int code;
        private final String desc;
        
        private FLType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum QueryType
    {
        Login(1, "登录"), 
        All(2, "全部"), 
        BasicLibrary(3, "基础库"), 
        StandardLibrary(4, "标准库"), 
        ElectronicFile(7, "电子档案"), 
        FXYPLibrary(8, "分析研判"), 
        FZBFLibrary(9, "辅助办案"), 
        BITJLibrary(10, "BI统计"), 
        APILibrary(11, "API接口"), 
        BusinessInfo(12, "综合查询-企业工商信息"), 
        TenderInfo(13, "综合查询-工程中标信息"), 
        CaseInfo(14, "综合查询-食品药品安全事故信息"), 
        AuthenticationInfo(15, "综合查询-司法鉴定机构"), 
        NotaryOffice(16, "综合查询-公证机构信息"), 
        LegalAidAgency(17, "综合查询-律师机构信息"), 
        ProjectTender(18, "专题库-工程招投标库"), 
        CaseLibrary(19, "专题库-案例库"), 
        LawsAndRegulations(20, "专题库-法律法规库"), 
        Archive(21, "专题库-档案库"), 
        VehicleViolationLibrary(22, "专题库-驾驶证关联违章信息库"), 
        VehicleViolationLibraryDetail(23, "专题库-驾驶证关联违章信息库详情"), 
        TrackLibrary(24, "专题库-监察对象轨迹库"), 
        AppLogin(25, "App登录"), 
        HumanAndSocial(26, "关联库-人社局关联查询"), 
        LegalJustices(27, "关联库-司法公正信息"), 
        Forensics(28, "关联库-司法鉴定信息"), 
        Project(29, "关联库-工程项目关联库"), 
        CorrelationAnalysis(30, "关联分析");
        
        private int code;
        private String value;
        
        private QueryType(final int code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public int getCode() {
            return this.code;
        }
        
        public void setCode(final int code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum InterfaceName
    {
        login("/jcwdl/api/login", "登录接口"), 
        appLogin("/app/v1.0/login", "App登录接口"), 
        auth("/jcwdl/api/auth", "auth登录接口"), 
        bool("/esQuery/bool", "PC端Es查询接口"), 
        byTag("/esQuery/byTag", "标准库查询"), 
        byTagForApp("/app/v1.0/esQuery/byTag", "标准库查询"), 
        boolForApp("/app/v1.0/esQuery/bool", "APP的综合查询接口"), 
        query("/oracleQuery/query", "PC端查看详情接口"), 
        queryForApp("/app/v1.0/oracleQuery/queryForApp", "APP查看详情接口"), 
        dzdaJbxx("/baseInfo/query", "电子档案-基本信息"), 
        JTZYCYXX("/listQuery/JTZYCYXX", "电子档案-家庭主要成员信息"), 
        CLQKM("/listQuery/CLQKM", "电子档案-车辆情况"), 
        FCQK("/listQuery/FCQK", "电子档案-房产情况"), 
        QGMHLG("/listQuery/QGMHLG", "电子档案-民航离港记录"), 
        QGTLSP("/listQuery/QGTLSP", "电子档案-全国铁路售票"), 
        SDXHD("/listQuery/SDXHD", "电子档案-短信话单"), 
        SYHJY("/listQuery/SYHJY", "电子档案-银行交易"), 
        SYYHD("/listQuery/SYYHD", "电子档案-语音话单"), 
        queryForStay("/listQuery/queryForStay", "电子档案-住宿"), 
        queryForRelation("/listQuery/queryForRelation", "电子档案-关联联系方式"), 
        queryForBank("/listQuery/queryForBank", "电子档案-银行信息"), 
        queryForSite("/listQuery/queryForSite", "电子档案-出行记录"), 
        queryForSiteDetail("/listQuery/queryForSiteDetail", "电子档案-出行记录详情"), 
        queryForSHQYGX("/listQuery/queryForSHQYGX", "电子档案-社会亲友关系"), 
        queryForSHQYGXDetail("/listQuery/queryForSHQYGXDetail", "电子档案-社会亲友关系详情"), 
        queryForGRSBXX("/listQuery/queryForGRSBXX", "电子档案-个人社保信息"), 
        exportForGABY("/gabyQuery/exportByStream", "电子档案-公安部云-导入excel"), 
        queryForGABY("/gabyQuery/query", "电子档案-公安部云-查询个人基本信息"), 
        downDefaultExcel("/dcbQuery/downDefaultExcel", "电子档案-调查表-下载excel默认文档"), 
        exportForDCB("/dcbQuery/exportByStream", "电子档案-调查库-导入excel"), 
        exportMultipart("/dcbQuery/exportMultipart", "电子档案-调查库-批量导入excel"), 
        queryForDCB("/dcbQuery/query", "电子档案-调查库-查询个人基本信息"), 
        queryByLibrary("/dcbQuery/queryByLibrary", "电子档案-调查库-查询委办局列表信息"), 
        deleteListForDCB("/dcbQuery/deleteListForDCB", "电子档案-调查库-批量删除个人基本信息"), 
        deleteForDCB("/dcbQuery/deleteForDCB", "电子档案-调查库-删除个人基本信息"), 
        queryForRHZXDZJZ("/biQuery/queryForRHZXDZJZ", "BI统计-融合中心-数据使用情况统计"), 
        queryForRHZXSJSL("/biQuery/queryForRHZXSJSL", "BI统计-融合中心-各市委办局数据被使用情况"), 
        queryForRHZXWBJ("/biQuery/queryForRHZXWBJ", "BI统计-融合中心-各市委办局数据被使用统计"), 
        queryForHJZXSJXXTJ("/biQuery/queryForHJZXSJXXTJ", "BI统计-汇集中心-数据信息统计"), 
        queryForHJZXSJCCFX("/biQuery/queryForHJZXSJCCFX", "BI统计-汇集中心-数据存储分析"), 
        queryForHJZXDSJFX("/biQuery/queryForHJZXDSJFX", "BI统计-汇集中心-大数据分析"), 
        queryForHJZXSJZL("/biQuery/queryForHJZXSJZL", "BI统计-汇集中心-各委办局数据总量"), 
        queryByHJZXKSJZL("/biQuery/queryByHJZXKSJZL", "BI统计-汇集中心-各委办局数据结构量"), 
        queryForHJZXSJAL("/biQuery/queryForHJZXSJAL", "BI统计-汇集中心-各委办局数据增量"), 
        queryForHJZXYQST("/biQuery/queryForHJZXYQST", "BI统计-汇集中心-数据量更新月趋势图"), 
        queryForHJZXZQST("/biQuery/queryForHJZXZQST", "BI统计-汇集中心-数据量更新周趋势图"), 
        queryByWJZXLSQSQK("/biQuery/queryByWJZXLSQSQK", "BI统计-挖掘中心-问题数据发现次数历史趋势情况"), 
        queryByWJZXSJZLQK("/biQuery/queryByWJZXSJZLQK", "BI统计-挖掘中心-数据质量情况分析"), 
        queryByWJZXFSLQSQK("/biQuery/queryByWJZXFSLQSQK", "BI统计-挖掘中心-问题发生率历史趋势情况"), 
        queryByWJZXWBJSJZB("/biQuery/queryByWJZXWBJSJZB", "BI统计-挖掘中心-各委办局问题数据占比top"), 
        queryByWJZXWTSJFLZB("/biQuery/queryByWJZXWTSJFLZB", "BI统计-挖掘中心-问题数据分类占比"), 
        queryByWJZXSJZLQKFX("/biQuery/queryByWJZXSJZLQKFX", "BI统计-挖掘中心-数据质量情况分析"), 
        queryForJCXTJBSJLX("/biQuery/queryForJCXTJBSJLX", "BI统计-监察系统-基本数据类型分析"), 
        queryForJCXTDZZJ("/biQuery/queryForJCXTDZZJ", "BI统计-监察系统-电子证据"), 
        queryForJCXTCXFX("/biQuery/queryForJCXTCXFX", "BI统计-监察系统-查询方式分析"), 
        queryForJCXTCXRD("/biQuery/queryForJCXTCXRD", "BI统计-监察系统-查询热度分析"), 
        queryForJCXTAJLFX("/biQuery/queryForJCXTAJLFX", "BI统计-监察系统-案件量分析"), 
        queryForJCXTAJJZTJ("/biQuery/queryForJCXTAJJZTJ", "BI统计-监察系统-案件进展统计"), 
        queryForJCXTSARYFB("/biQuery/queryForJCXTSARYFB", "BI统计-监察系统-涉案人员分布"), 
        queryForJCXTSAAJFL("/biQuery/queryForJCXTSAAJFL", "BI统计-监察系统-涉案案件分析"), 
        queryForJCXTSAZWFL("/biQuery/queryForJCXTSAZWFL", "BI统计-监察系统-涉案职位分析"), 
        queryForJCXTWBJBL("/biQuery/queryForJCXTWBJBL", "BI统计-监察系统-覆盖委办局比例"), 
        queryForJCXTQYAJFX("/biQuery/queryForJCXTQYAJFX", "BI统计-监察系统-区域案件分析"), 
        queryForJCXTJCRYTJ("/biQuery/queryForJCXTJCRYTJ", "BI统计-监察系统-各委办局检测人员统计"), 
        queryForFLFG("/fzbaQuery/queryForFLFG", "辅助办案-法律法规"), 
        queryForZFAL("/fzbaQuery/queryForZFAL", "辅助办案-执法案例"), 
        queryOneFLFG("/fzbaQuery/queryOneFLFG", "辅助办案-单条法律法规"), 
        queryOneZFAL("/fzbaQuery/queryOneZFAL", "辅助办案-单条执法案例"), 
        exportByFLFG("/fzbaQuery/exportByFLFG", "辅助办案-导入法律法规"), 
        exportByZFAL("/fzbaQuery/exportByZFAL", "辅助办案-导入执法案例"), 
        queryFZBACount("/fzbaQuery/queryFZBACount", "辅助办案-首页数量信息"), 
        queryFZBAInfo("/fzbaQuery/queryFZBAInfo", "辅助办案-首页近期更新信息"), 
        queryForAPITable("/apiQuery/query", "API接口-根据表名查询数据"), 
        queryForBaseInfo("/baseInfo/query", "电子档案-基本信息"), 
        queryForIdentifier("/baseInfo/queryForIdentifier", "电子档案-重要识别号查询"), 
        getIdentifierDetail("/baseInfo/getIdentifierDetail", "电子档案-重要识别号详情查询"), 
        getFCXXDetail("/baseInfo/getFCXXDetail", "电子档案-房屋详情接口调用"), 
        queryForLastActivitySituation("/baseInfo/queryForLastActivitySituation", "电子档案-最后一次活动情况接口"), 
        queryForActivitySituation("/baseInfo/queryForActivitySituation", "电子档案-活动情况接口"), 
        queryForActiveArea("/baseInfo/queryForActiveArea", "电子档案-活动区域接口"), 
        queryForTimingDiagram("/baseInfo/queryForTimingDiagram", "电子档案-活动时序图"), 
        queryForAnalysisJudgment("/analysisJudgment/query", "分析研判-搜索结果页查询"), 
        AnalysisJudgmentWithAuth("/analysisJudgment/auth", "分析研判-生成电子档案"), 
        projectTender("/specialLibrary/projectTender", "专题库-工程招投标库查询"), 
        lawsAndRegulations("/specialLibrary/lawsAndRegulations", "专题库-法律法规库查询"), 
        caseLibrary("/specialLibrary/caseLibrary", "专题库-案例库查询"), 
        archive("/specialLibrary/archive", "专题库-档案库查询"), 
        vehicleViolationLibrary("/specialLibrary/vehicleViolationLibrary", "专题库-驾驶证关联违章信息库查询"), 
        vehicleViolationLibraryDetail("/specialLibrary/vehicleViolationLibraryDetail", "专题库-驾驶证关联违章信息库详情查询"), 
        trackLibrary("/specialLibrary/trackLibrary", "专题库-监察对象轨迹库查询"), 
        humanAndSocial("/associationLibrary/humanAndSocial", "关联库-人社局关联查询"), 
        legalJustices("/associationLibrary/legalJustices", "关联库-司法公正信息"), 
        forensics("/associationLibrary/forensics", "关联库-司法鉴定信息"), 
        queryForAssociationLibrary("/associationLibrary/query", "关联库查询"), 
        queryForCorrelationAnalysis("/correlationAnalysis/query", "关联分析查询");
        
        private String code;
        private String value;
        
        private InterfaceName(final String code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public String getCode() {
            return this.code;
        }
        
        public void setCode(final String code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum DataStatistics
    {
        OptionalTime(0, "自选时间"), 
        Today(1, "今天"), 
        Yesterday(2, "昨天"), 
        CurrentMonth(3, "本月"), 
        LastSevenDays(4, "最近7天"), 
        LastThirtyDays(5, "最近30天");
        
        private int code;
        private String value;
        
        private DataStatistics(final int code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public int getCode() {
            return this.code;
        }
        
        public void setCode(final int code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum IdentifierType
    {
        Tel(1, "手机"), 
        Car(2, "车辆"), 
        Bank(3, "银行卡");
        
        private int code;
        private String value;
        
        private IdentifierType(final int code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public int getCode() {
            return this.code;
        }
        
        public void setCode(final int code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum UserInfoTable
    {
        HYDJ("HYDJ", "婚姻登记信息"), 
        ZWY_RYXX_GRJBXX("ZWY_RYXX_GRJBXX", "个人基本信息"), 
        WH_LICENSE_SYNC_VIEW("WH_LICENSE_SYNC_VIEW", "特种行业人员操作资格信息"), 
        GWHYZSXX("GWHYZSXX", "高危行业证书信息"), 
        WHSJGRYXX("WHSJGRYXX", "武汉市人员信息记录"), 
        FCCX_FKXX_BA("FCCX_FKXX_BA", "备案信息"), 
        PEOPLE_HJRK("PEOPLE_HJRK", "户籍人口基本信息"), 
        LDRKSJ_LDRK("LDRKSJ_LDRK", "流动人口基本信息"), 
        PEOPLE_HYZK("PEOPLE_HYZK", "户籍人婚姻史信息"), 
        YYHD("YYHD", "语音话单"), 
        LDGBGRXX("LDGBGRXX", "领导干部个人信息"), 
        ZZB_SFJ_MDJF("ZZB_SFJ_MDJF", "全市社会治安综合治理信息"), 
        YHYHXX("YHYHXX", "银行用户信息");
        
        private String code;
        private String value;
        
        private UserInfoTable(final String code, final String value) {
            this.code = code;
            this.value = value;
        }
        
        public String getCode() {
            return this.code;
        }
        
        public void setCode(final String code) {
            this.code = code;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(final String value) {
            this.value = value;
        }
    }
    
    public enum HJZXType
    {
        SJXXTJ("xxtj", "数据信息统计"), 
        SJCCFX("ccfx", "数据存储分析"), 
        DSJFX("dsj", "大数据分析"), 
        YQST("yqst", "月趋势图"), 
        ZQST("zqst", "周趋势图"), 
        KZL("kzl", "库数据总量");
        
        private String code;
        private final String desc;
        
        private HJZXType(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public String GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum JCXTType
    {
        JBSJLX("jbsjlx", "基本数据类型"), 
        DZZJ("dzzj", "电子证据"), 
        CXFX("cxfx", "查询方式分析"), 
        CXRD("cxrd", "查询热度分析"), 
        AJLFX("ajlfx", "案件量分析"), 
        AJJZTJ("ajjztj", "案件进展统计"), 
        SARYFB("saryfb", "涉案人员分布"), 
        SAAJFL("saajfl", "涉案案件分析"), 
        SAZWFL("sazwfl", "涉案职位分析"), 
        WBJBL("wbjbl", "覆盖委办局比例");
        
        private String code;
        private final String desc;
        
        private JCXTType(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public String GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum ColumnType
    {
        English(0, "英文字段"), 
        Chinese(1, "中文字段");
        
        private int code;
        private final String desc;
        
        private ColumnType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum AnalysisJudgmentQueryType
    {
        QueryContent(1, "查询内容"), 
        QueryCounts(2, "查询数量"), 
        FuzzyQuery(3, "模糊查询"), 
        AssociatedQuery(4, "关联查询");
        
        private int code;
        private final String desc;
        
        private AnalysisJudgmentQueryType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum AnalysisJudgmentType
    {
        UserInfo(1, "人员信息"), 
        AssetInfo(2, "资产信息"), 
        SocialInfo(3, "社会信息"), 
        MechanismInfo(4, "机构信息"), 
        CommunicationInfo(6, "通讯信息"), 
        CaseInfo(7, "涉案信息"), 
        TrajectoryInfo(8, "轨迹信息");
        
        private int code;
        private final String desc;
        
        private AnalysisJudgmentType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum FZBAType
    {
        zfal("zfalType"), 
        flfg("flfgType");
        
        private String code;
        
        private FZBAType(final String code) {
            this.code = code;
        }
        
        public String GetCode() {
            return this.code;
        }
    }
    
    public enum FlagEnum
    {
        None(Integer.valueOf(0), "无业务"), 
        Secret(Integer.valueOf(1), "涉密"), 
        SecretAuthority(Integer.valueOf(2), "涉密授权"), 
        Red(Integer.valueOf(5), "红名单"), 
        RedAuthority(Integer.valueOf(6), "红名单授权");
        
        private Integer code;
        private final String desc;
        
        private FlagEnum(final Integer code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public Integer GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum EncryptField
    {
        desc_pretty(Integer.valueOf(1), "desc_pretty"), 
        pk_col_data(Integer.valueOf(2), "pk_col_data"), 
        pk_col_name(Integer.valueOf(3), "pk_col_name"), 
        db_user(Integer.valueOf(4), "db_user"), 
        db_nick_name(Integer.valueOf(5), "db_nick_name"), 
        table_name(Integer.valueOf(6), "table_name"), 
        table_nick_name(Integer.valueOf(7), "table_nick_name");
        
        private Integer code;
        private final String desc;
        
        private EncryptField(final Integer code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public Integer GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum IsFirstLogin
    {
        No(Integer.valueOf(0), "N"), 
        Yes(Integer.valueOf(1), "Y");
        
        private Integer code;
        private final String desc;
        
        private IsFirstLogin(final Integer code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public Integer GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum IsLock
    {
        No(Integer.valueOf(0), "N"), 
        Yes(Integer.valueOf(1), "Y");
        
        private Integer code;
        private final String desc;
        
        private IsLock(final Integer code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public Integer GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum Action
    {
        login("/jcwdl/api/login"), 
        auth("/jcwdl/api/auth"), 
        loginTest("/api/loginTest"), 
        appLogin("/app/v1.0/login"), 
        other("other"), 
        special("special");
        
        private String code;
        
        private Action(final String code) {
            this.code = code;
        }
        
        public String GetCode() {
            return this.code;
        }
    }
    
    public enum StatusType
    {
        Disable(0, "禁用"), 
        Enable(1, "启用");
        
        private int code;
        private final String desc;
        
        private StatusType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum HdfsOP
    {
        CREATE("创建文件/目录", 0), 
        RENAME("重命名文件/目录", 1), 
        COPY("复制文件/目录", 2), 
        MOVE("移动文件/目录", 3), 
        DELETE("删除文件/目录", 4), 
        EMPTYTRASH("清空回收站", 5), 
        LISTSTATUS("获取文件/目录状态列表", 6), 
        OPEN("打开文件", 7), 
        WRITE("写入内容", 8), 
        APPEND("追加内容", 9), 
        UPLOAD("上传文件或目录", 10), 
        DOWNLOAD("下载文件或目录", 11), 
        FILElIST("文件列表状态", 12), 
        HOMELIST("主用户文件列表状态", 13), 
        TRASHLIST("垃圾回收站文件列表状态", 14);
        
        private final String name;
        private final int value;
        
        private HdfsOP(final String name, final int value) {
            this.value = value;
            this.name = name;
        }
        
        @JsonCreator
        public static HdfsOP getEnum(final int value) {
            for (final HdfsOP op : values()) {
                if (op.getValue() == value) {
                    return op;
                }
            }
            return null;
        }
        
        @JsonValue
        public int getValue() {
            return this.value;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public enum TableType
    {
        RSJ_INFO(1, "RSJ_INFO"), 
        SFJ_SFGZXX_INFO(2, "SFJ_SFGZXX_INFO"), 
        SFJ_JDXX_INFO(3, "SFJ_JDXX_INFO"), 
        NEWBIINFO(4, "NEWBIINFO");
        
        private int code;
        private final String desc;
        
        private TableType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
    
    public enum AnalysisType
    {
        CompanyName(1, "公司名称"), 
        ProjectName(2, "项目名称"), 
        ProjectAdd(3, "项目地址");
        
        private int code;
        private final String desc;
        
        private AnalysisType(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int GetCode() {
            return this.code;
        }
        
        public String GetDesc() {
            return this.desc;
        }
    }
}
