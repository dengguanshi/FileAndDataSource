import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class FileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("deprecation")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器处理上传的文件
        // 1创建DiskFileUpload
        DiskFileUpload diskFileUpload = new DiskFileUpload();
        // 2 通过上面创建的类去解析请求
        List<FileItem> list = null;
        try {
            list = diskFileUpload.parseRequest(request);
        } catch (FileUploadException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Map<String, String> map = new HashMap<String, String>();
        // 3遍历解析请求后的List
        assert list != null;
        for (FileItem fileItem : list) {
            //FileItem提供了一个 用于判断是文件还是普通的文本
            if(fileItem.isFormField()) //fileItem.isFormField() 返回true就是文本
            {
                //获取输入框的值 以及 输入框的name属性
                // 获得表单的元素名 <input name="username">
                String fieldName = fileItem.getFieldName();
                String fieldValue = fileItem.getString();//getString(“UTF-8”)
                System.out.println(fieldName +" "+fieldValue);
                map.put(fieldName, fieldValue);

            }else{ //就是文件  保存到我们服务器的硬盘上
                //位置自定义 但是不建议大家存在tomcat相关的目录
                //生成一下文件名 （文件名不能重读）
                //时间错+原来文件的名字
                // 不是普通的表单元素 文件域 getName 返回原来文件的名字
                String oldFileName = fileItem.getName();
                System.out.println("oldFileName " +oldFileName);
                //为防止一个目录下面出现太多文件 我们采用时间来创建目录

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dataStr = sdf.format(new Date());
                String dataDir = dataStr.split(" ")[0];
                //逻辑 先判断当前的目录  有没有 有就不创建  没有就创建
                File file1 = new File("G:/test file/"+dataDir);
                if(!file1.exists()) //不存在就创建
                {
                    file1.mkdir();
                }
                oldFileName = System.currentTimeMillis()+oldFileName;
                File file = new File("D:/file_upload/"+dataDir+"/"+oldFileName);
                //通过wirte写入到某个位置
                try {


                    fileItem.write(file);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //插入数据库

        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
