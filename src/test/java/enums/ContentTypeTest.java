package enums;

import org.junit.Assert;
import org.junit.Test;

public class ContentTypeTest {

    @Test
    public void testForTheRightContentTypeByExtension() {
        Assert.assertEquals(ContentType.HTML, ContentType.valueOf("HTML"));
        Assert.assertEquals(ContentType.JSON, ContentType.valueOf("JSON"));
    }

}