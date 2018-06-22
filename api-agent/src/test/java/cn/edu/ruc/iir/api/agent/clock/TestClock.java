package cn.edu.ruc.iir.api.agent.clock;

import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.clock
 * @ClassName: TestClock
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-16 11:09
 **/
public class TestClock {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("python.home", "/usr/lib/python3.6");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("a=[5,2,3,9,4,0]; ");
        interpreter.exec("print(sorted(a));");  //此处python语句是3.x版本的语法
    }

    @Test
    public void testClock() {
        Properties props = new Properties();
        props.put("python.home", "/usr/lib/python3.6");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);

        // 1. Python面向函数式编程: 在Java中调用Python函数
        String pythonFunc = "/home/tao/software/pyspace/api-function/bash/fun.py";
        PythonInterpreter pi1 = new PythonInterpreter();
        // 加载python程序
        pi1.execfile(pythonFunc);
        // 调用Python程序中的函数
        PyFunction pyf = pi1.get("add", PyFunction.class);
        PyObject dddRes = pyf.__call__(Py.newInteger(2), Py.newInteger(3));
        System.out.println(dddRes);
        pi1.cleanup();
        pi1.close();
    }

    @Test
    public void testPythonByProgress() throws IOException {
        String cmd = "python /home/tao/software/pyspace/api-function/main.py";
        Process proc = Runtime.getRuntime().exec(cmd); //执行py文件
        InputStreamReader stdin = new InputStreamReader(proc.getInputStream(), "UTF-8");
        BufferedReader input = new BufferedReader(stdin);

        String line = null;
        while ((line = input.readLine()) != null) {
            System.out.println(line);//得到输出
        }
    }


}
