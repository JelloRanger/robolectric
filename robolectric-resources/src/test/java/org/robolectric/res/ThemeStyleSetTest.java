package org.robolectric.res;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThemeStyleSetTest {

  private ThemeStyleSet themeStyleSet;

  @Before
  public void setUp() throws Exception {
    themeStyleSet = new ThemeStyleSet();
  }

  @Test
  public void shouldFindAttributesFromAnAppliedStyle() throws Exception {
    themeStyleSet = new ThemeStyleSet();
    themeStyleSet.apply(createStyle("style1",
        createAttribute("string1", "string1 value from style1"),
        createAttribute("string2", "string2 value from style1")
    ), false);
    themeStyleSet.apply(createStyle("style2", createAttribute("string2", "string2 value from style2")), false);
    assertThat(themeStyleSet.getAttrValue(1).value).isEqualTo("string1 value from style1");
    assertThat(themeStyleSet.getAttrValue(2).value).isEqualTo("string2 value from style1");
  }

  @Test
  public void shouldFindAttributesFromAnAppliedFromForcedStyle() throws Exception {
    themeStyleSet.apply(createStyle("style1",
        createAttribute("string1", "string1 value from style1"),
        createAttribute("string2", "string2 value from style1")
    ), false);
    themeStyleSet.apply(createStyle("style2", createAttribute("string1", "string1 value from style2")), true);
    assertThat(themeStyleSet.getAttrValue(1).value).isEqualTo("string1 value from style2");
    assertThat(themeStyleSet.getAttrValue(2).value).isEqualTo("string2 value from style1");
  }

  private StyleData createStyle(String styleName, AttributeResource... attributeResources) {
    StyleData styleData = new StyleData("package", styleName, null);
    for (int i = 0, attributeResourcesLength = attributeResources.length; i < attributeResourcesLength; i++) {
      AttributeResource attributeResource = attributeResources[i];
      styleData.add(i + 1, attributeResource);
    }
    return styleData;
  }

  private AttributeResource createAttribute(String attrName, String value) {
    return new AttributeResource(attrName(attrName), value, "package");
  }

  private ResName attrName(String attrName) {
    return new ResName("package", "attr", attrName);
  }
}