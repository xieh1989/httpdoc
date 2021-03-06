package io.httpdoc.objc.fragment;

import io.httpdoc.core.Preference;
import io.httpdoc.core.appender.EnterMergedAppender;
import io.httpdoc.core.appender.IndentAppender;
import io.httpdoc.core.appender.LineAppender;
import io.httpdoc.core.fragment.Fragment;
import io.httpdoc.objc.ObjC;

import java.io.IOException;
import java.util.*;

/**
 * 枚举定义代码碎片
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-07-24 17:36
 **/
public class ObjCEnumDefineFragment implements Fragment {
    private ObjCCommentFragment commentFragment;
    private String name;
    private Set<EnumConstantFragment> constantFragments = new LinkedHashSet<>();

    public ObjCEnumDefineFragment() {
    }

    public ObjCEnumDefineFragment(String name) {
        this.name = name;
    }

    public ObjCEnumDefineFragment(String note, String name) {
        this.commentFragment = note != null ? new ObjCCommentFragment(note) : null;
        this.name = name;
    }

    public ObjCEnumDefineFragment(ObjCCommentFragment commentFragment, String name) {
        this.commentFragment = commentFragment;
        this.name = name;
    }

    @Override
    public Set<String> imports() {
        Set<String> imports = new TreeSet<>();
        for (EnumConstantFragment constant : constantFragments) imports.addAll(constant.imports());
        return imports;
    }

    @Override
    public <T extends LineAppender<T>> void joinTo(T appender, Preference preference) throws IOException {
        if (commentFragment != null) commentFragment.joinTo(appender, preference);

        for (String include : imports()) appender.append(include).enter();

        appender.append("typedef NS_ENUM(NSInteger, ").append(name).append("){").enter();

        EnterMergedAppender indented = new EnterMergedAppender(new IndentAppender(appender, preference.getIndent()), 2);
        Iterator<EnumConstantFragment> iterator = constantFragments.iterator();
        while (iterator.hasNext()) {
            EnumConstantFragment fragment = iterator.next();
            fragment.joinTo(indented, preference);
            if (iterator.hasNext()) indented.append(",");
            indented.enter();
        }
        indented.close();

        appender.append("};");
    }

    public ObjCEnumDefineFragment addConstantFragment(String name) {
        return addConstantFragment(new EnumConstantFragment(name));
    }

    public ObjCEnumDefineFragment addConstantFragment(String name, Integer value) {
        return addConstantFragment(new EnumConstantFragment(name, value));
    }

    public ObjCEnumDefineFragment addConstantFragment(String comment, String name) {
        return addConstantFragment(new EnumConstantFragment(comment, name));
    }

    public ObjCEnumDefineFragment addConstantFragment(String comment, String name, Integer value) {
        return addConstantFragment(new EnumConstantFragment(comment, name, value));
    }

    public ObjCEnumDefineFragment addConstantFragment(ObjCCommentFragment commentFragment, String name, Integer value) {
        return addConstantFragment(new EnumConstantFragment(commentFragment, name, value));
    }

    public ObjCEnumDefineFragment addConstantFragment(EnumConstantFragment constantFragment) {
        constantFragments.add(constantFragment);
        return this;
    }

    public ObjCCommentFragment getCommentFragment() {
        return commentFragment;
    }

    public ObjCEnumDefineFragment setCommentFragment(ObjCCommentFragment commentFragment) {
        this.commentFragment = commentFragment;
        return this;
    }

    public String getName() {
        return name;
    }

    public ObjCEnumDefineFragment setName(String name) {
        this.name = name;
        return this;
    }

    public Set<EnumConstantFragment> getConstantFragments() {
        return constantFragments;
    }

    public ObjCEnumDefineFragment setConstantFragments(Set<EnumConstantFragment> constantFragments) {
        this.constantFragments = constantFragments;
        return this;
    }

    /**
     * 枚举常量代码碎片
     *
     * @author 杨昌沛 646742615@qq.com
     * @date 2018-07-24 17:47
     **/
    public static class EnumConstantFragment implements Fragment {
        private ObjCCommentFragment commentFragment;
        private String name;
        private Integer value;

        public EnumConstantFragment() {
        }

        public EnumConstantFragment(String name) {
            this(name, (Integer) null);
        }

        public EnumConstantFragment(String name, Integer value) {
            this((String) null, name, value);
        }

        public EnumConstantFragment(String comment, String name) {
            this(comment, name, null);
        }

        public EnumConstantFragment(String comment, String name, Integer value) {
            this(comment != null ? new ObjCCommentFragment(comment) : null, name, value);
        }

        public EnumConstantFragment(ObjCCommentFragment commentFragment, String name, Integer value) {
            this.commentFragment = commentFragment;
            this.name = name;
            this.value = value;
        }

        @Override
        public Set<String> imports() {
            return Collections.singleton("#import " + ObjC.FOUNDATION);
        }

        @Override
        public <T extends LineAppender<T>> void joinTo(T appender, Preference preference) throws IOException {
            if (commentFragment != null) commentFragment.joinTo(appender, preference);
            appender.append(name);
            if (value != null) appender.append(" = ").append(String.valueOf(value));
        }

        public ObjCCommentFragment getCommentFragment() {
            return commentFragment;
        }

        public EnumConstantFragment setCommentFragment(ObjCCommentFragment commentFragment) {
            this.commentFragment = commentFragment;
            return this;
        }

        public String getName() {
            return name;
        }

        public EnumConstantFragment setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getValue() {
            return value;
        }

        public EnumConstantFragment setValue(Integer value) {
            this.value = value;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EnumConstantFragment that = (EnumConstantFragment) o;

            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

}
