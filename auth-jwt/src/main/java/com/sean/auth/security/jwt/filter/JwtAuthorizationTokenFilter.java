package com.sean.auth.security.jwt.filter;

import com.sean.auth.security.jwt.JwtTokenUtil;
import com.sean.auth.security.jwt.JwtUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import com.sean.auth.security.jwt.config.FilterIgnorePropertiesConfig;
/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/11/6 9:50
 */
@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    private OrRequestMatcher orRequestMatcher;

    @Autowired
    private UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final String tokenHeader;

    private int expiration;

//    @Autowired
//    private RedisManager redisManager;

    @PostConstruct
    public void init() {
// 初始化忽略的url不走过此滤器
        List<RequestMatcher> matchers = filterIgnorePropertiesConfig.getUrls().stream()
                .map(url -> new AntPathRequestMatcher(url))
                .collect(Collectors.toList());
        orRequestMatcher = new OrRequestMatcher(matchers);
    }

    public JwtAuthorizationTokenFilter(JwtTokenUtil jwtTokenUtil, @Value("${jwt.header:token}") String tokenHeader, @Value("${jwt.expiration:20000}") Long expire) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
        this.expiration = (int) (expire / 1000);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.debug("processing authentication for '{}'", requestURI);
        final String requestHeader = request.getHeader(this.tokenHeader);

        JwtUserDetails jwtUser = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                jwtUser = jwtTokenUtil.getJwtUserFromToken(authToken);
            } catch (ExpiredJwtException e) {
                // token 过期
                throw new AccountExpiredException("登陆状态已过期");
            } catch (MalformedJwtException e) {
                log.info("解析前端传过来的Authentication错误，但不影响业务逻辑！token:{}", requestHeader);
            } catch (Exception e) {
                log.info("JwtAuthorizationTokenFilter处理异常！{}", e.getMessage());
            }
        }
        log.debug("checking authentication for user '{}'", jwtUser);

        //生成jwt的token的过期时间是一天，而这里控制实际过期时间是两个小时（application.yml配置的过期时间）
        if (jwtUser != null && jwtUser.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            if (redisManager.exists(CacheAdminConstant.USER_AUTHORITY_NOT_EXPIRED + authToken)) {
//                redisManager.expire(CacheAdminConstant.USER_AUTHORITY_NOT_EXPIRED + authToken, expiration);
//            } else {
//                throw new AccountExpiredException("登录信息已经过期或已经退出登录，请重新登录！");
//            }

            UserDetails user = userDetailsService.loadUserByUsername(jwtUser.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.debug("authorizated user '{}', setting security context", user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * 可以重写
     * @param request
     * @return 返回为true时，则不过滤即不会执行doFilterInternal
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return orRequestMatcher.matches(request);
    }

}
