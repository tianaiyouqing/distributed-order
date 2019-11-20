package cloud.tianai.order.core.ant;

import org.springframework.util.AntPathMatcher;

public class AntMatcherTest {

    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String path = "/v1/user/add/001";
        String pattern = "/v1/{user}/**/001";

        System.out.println(antPathMatcher.isPattern(pattern));
        boolean match = antPathMatcher.match(pattern, path);
        System.out.println(match);
        System.out.println(antPathMatcher.matchStart(pattern, path));

        System.out.println(antPathMatcher.extractPathWithinPattern(pattern, path));
        System.out.println(antPathMatcher.extractUriTemplateVariables(pattern, path));

        System.out.println(antPathMatcher.getPatternComparator(pattern));

    }
}
