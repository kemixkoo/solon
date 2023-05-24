package com.github.xiaoymin.knife4j.solon.extension;

import org.noear.solon.docs.ApiVendorExtension;

/**
 * @author noear
 * @since 2.2
 */
public class ApiAuthorExtension implements ApiVendorExtension<String> {
    private final String author;

    public ApiAuthorExtension(String author) {
        this.author = author;
    }

    public String getName() {
        return "x-author";
    }

    public String getValue() {
        return this.author;
    }
}