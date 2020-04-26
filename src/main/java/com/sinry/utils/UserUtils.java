

package com.sinry.utils;


public class UserUtils {
//    private static final Logger log = LoggerFactory.getLogger(UserUtils.class);
//    public static final String ORGAN_HEADER = "x-organ-Id";
//    public static final String AUTHORIZATION_HEADER = "authorization";
//    public static final String USER_ID = "x-user-id";
//    public static final String TENANT_ID = "x-tenant-id";
//
//    public UserUtils() {
//    }
//
//    public static String getUserId() throws Exception {
//        String userId = getHeader("x-user-id");
//        if (StringUtils.isEmpty(userId)) {
//            userId = parseJwt().getSubject();
//        }
//
//        return userId;
//    }
//
//    public static String getTenantId() throws Exception {
//        String tenantId = getHeader("x-tenant-id");
//        if (StringUtils.isEmpty(tenantId)) {
//            tenantId = parseJwt().getStringClaim("role");
//        }
//
//        return tenantId;
//    }
//
//    public static Long getLongUserId() throws Exception {
//        String userId = getUserId();
//        return StringUtils.isEmpty(userId) ? null : Long.parseLong(getUserId());
//    }
//
//    public static Long getLongOrganId() {
//        String organId = getOrganId();
//        return StringUtils.isEmpty(organId) ? null : Long.parseLong(organId);
//    }
//
//    public static Long getLongTenantId() throws Exception {
//        String tenantId = getTenantId();
//        return StringUtils.isEmpty(tenantId) ? null : Long.parseLong(tenantId);
//    }
//
//    public static String getOrganId() {
//        return getHeader("x-organ-Id");
//    }
//
//    public static String getToken() {
//        return getHeader("authorization");
//    }
//
//    private static String getHeader(String key) {
//        return Global.getCurrentRequest().getHeader(key);
//    }

//    public static User getSimpleUser() throws Exception {
//        return getService().getSimpleCurrentUser(getUserId());
//    }
//
//    private static JWTClaimsSet parseJwt() throws Exception {
//        return JwtUtils.getJWTClaimsSet(getToken());
//    }
//
//    public static User getUser() throws Exception {
//        Long organId = getLongOrganId();
//        return getService().getCurrentUser(getLongTenantId(), organId != null ? organId : 0L, getUserId());
//    }
//
//    private static AuthorizationClientService getService() {
//        return (AuthorizationClientService)Global.getBean(AuthorizationClientService.class);
//    }
}
