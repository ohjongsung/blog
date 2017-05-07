package io.ohjongsung;

/**
 * Created by ohjongsung on 2017-05-06. BolgAppication에서 사용하는 프로파일 정보. 특정 프로파일이 지정되지 않는다면,
 * 스탠드얼론이 활성화 된다. 이는 마리아 DB를 사용하지 않고, in-memory db를 사용한다.
 */
public class BlogProfiles {

    public static final String STAGING = "staging";

    public static final String PRODUCTION = "production";

    public static final String DEVELOPMENT = "development";
}
