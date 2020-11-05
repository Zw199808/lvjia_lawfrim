package com.xinou.lawfrim.common.util.XssAndSqlFilter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by xiao_XX on date 2020/06/09.
 * Description:
 */
public class ParamsServletInputStream extends ServletInputStream {

    private ByteArrayInputStream bis;

    public ParamsServletInputStream(ByteArrayInputStream bis) {
        this.bis = bis;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return bis.read();
    }

}