package com.lznbys.android.oss.config;

public interface ConfigJson {
    interface Config {
        String json = "{\n" +
                "  \"AccessKeyID\": \"LTAIDQd3QKChKO40\",\n" +
                "  \"AccessKeySecret\": \"LTAIDQd3QKChKO40nLsUm90CYyfDyyeKLlu5GuSNaQIooS\",\n" +
                "  \"RoleArn\": \"acs:ram::1237998230623237:role/aliyunosstokengeneratorrole\",\n" +
                "  \"TokenExpireTime\": \"900\",\n" +
                "  \"PolicyFile\": \"policy/all_policy.txt\"\n" +
                "}\n";
    }
    /***
     * 此处权限为AMR动态账号的权限,即移动端需要的权限
     */
    interface AllPolicy {
        String json = "{\n" +
                "  \"Version\": \"1\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Action\": [\n" +
                "        \"oss:*\"\n" +
                "      ],\n" +
                "      \"Resource\": \"*\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

//        String json = "{\n" +
//                "  \"Version\": \"1\",\n" +
//                "  \"Statement\": [\n" +
//                "    {\n" +
//                "      \"Effect\": \"Allow\",\n" +
//                "      \"Action\": [\n" +
//                "        \"sts:AssumeRole\",\n" +
//                "        \"oss:*\"\n" +
//                "      ],\n" +
//                "      \"Resource\": \"*\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
    }
    interface bucketReadPolicy {
        String json = "{\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": [\n" +
                "        \"oss:GetObject\",\n" +
                "        \"oss:ListObjects\"\n" +
                "      ],\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Resource\": [\"acs:oss:*:*:$BUCKET_NAME/*\", \"acs:oss:*:*:$BUCKET_NAME\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Version\": \"1\"\n" +
                "}\n";
    }
    interface bucketReadWrite {
        String json = "{\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": [\n" +
                "        \"oss:GetObject\",\n" +
                "        \"oss:PutObject\",\n" +
                "        \"oss:DeleteObject\",\n" +
                "        \"oss:ListParts\",\n" +
                "        \"oss:AbortMultipartUpload\",\n" +
                "        \"oss:ListObjects\"\n" +
                "      ],\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Resource\": [\"acs:oss:*:*:$BUCKET_NAME/*\", \"acs:oss:*:*:$BUCKET_NAME\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Version\": \"1\"\n" +
                "}\n";
    }
}
