package io.httpdoc.objective.c.fragment;

import io.httpdoc.core.Preference;
import io.httpdoc.core.appender.LineAppender;
import io.httpdoc.core.fragment.CommentFragment;
import io.httpdoc.objective.c.type.ObjCClass;

import java.io.IOException;

/**
 * Objective-C 字符串枚举常量 代码碎片
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-07-12 13:19
 **/
public class ObjCConstantDefineFragment extends ObjCConstantFragment {

    public ObjCConstantDefineFragment(ObjCClass clazz) {
        super(clazz);
    }

    public ObjCConstantDefineFragment(String name, ObjCClass clazz) {
        super(name, clazz);
    }

    public ObjCConstantDefineFragment(CommentFragment commentFragment, String name, ObjCClass clazz) {
        super(commentFragment, name, clazz);
    }

    @Override
    public <T extends LineAppender<T>> void joinTo(T appender, Preference preference) throws IOException {
        if (commentFragment != null) commentFragment.joinTo(appender, preference);
        String enumName = clazz.getSimpleName();
        appender.append("FOUNDATION_EXPORT ").append(enumName).append(" const ").append(enumName).append(name).append(";");
    }
}
