

package com.oozol.utils;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.MalformedURLException;
import java.net.URL;

public class JwtUtils {
    private static JWKSource jwkSource;
    private static final String JWT_CLAIM_ROLE = "role";

    public JwtUtils() {
    }

    public static JWTClaimsSet verifyToken(String token, JWKSource jwkSource) throws Exception {
        SignedJWT jwt = SignedJWT.parse(token);
        JWTClaimsSet cs = jwt.getJWTClaimsSet();
        Algorithm alg = jwt.getHeader().getAlgorithm();
        String clientId = (String)cs.getClaims().get("role");
        ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
        JWSAlgorithm expectedJWSAlg = JWSAlgorithm.parse(alg.getName());
        JWSKeySelector keySelector = new JWSVerificationKeySelector(expectedJWSAlg, jwkSource);
        keySelector.selectJWSKeys(jwt.getHeader(), (SecurityContext)null);
        jwtProcessor.setJWSKeySelector(keySelector);
        JWTClaimsSet claimsSet = jwtProcessor.process(token, (SecurityContext)null);
        return claimsSet;
    }

    public static JWKSource getJWKSource(String jwkUrl) throws MalformedURLException {
        if (jwkSource == null) {
            ResourceRetriever resourceRetriever = new DefaultResourceRetriever(5000, 5000, 512000);
            URL url = new URL(jwkUrl);
            jwkSource = new RemoteJWKSet(url, resourceRetriever);
        }

        return jwkSource;
    }

    public static JWTClaimsSet getJWTClaimsSet(String token) throws Exception {
        SignedJWT jwt = SignedJWT.parse(token);
        return jwt.getJWTClaimsSet();
    }

    public static JWKSource getJWKSource(RSAKey jwk) throws MalformedURLException {
        return new ImmutableJWKSet(new JWKSet(jwk));
    }
}
