package com.kanokun.ctc.utils;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.kanokun.cts.utils.DateUtil;

public class DateUtilTest {
  @Test
  public void test4() throws ParseException {
    Date dt = DateUtil.parseDate("03-Aug-2015");
    Date dt3 = DateUtil.parseDate("03-08-2015");
    Assert.assertTrue(dt.equals(dt3));
  }

}
