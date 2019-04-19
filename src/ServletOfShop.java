import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
@WebServlet( "/addShops")
public class ServletOfShop extends HttpServlet {
    private static Connection con = null;
    private static PreparedStatement ps =null;
    private  static ResultSet rs = null;
    public static HashSet<Shops>shops = new HashSet <>();
    String Path;
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        //得到上传文件的保存目录
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File file = new File(savePath);
        //判断上传的文件目录是否存在
        if (!file.exists()&&file.isDirectory()){
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();

        }
        //消息提示
        String message = "";
        //1.创建DiskFIleItemFactory工厂
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        //2.创建一个文件上传解析器
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        //防止中文名乱码
        servletFileUpload.setHeaderEncoding("UTF-8");
        //3.判断提交的数据是否是上传表单的数据
        if (!ServletFileUpload.isMultipartContent(request)){
            //按照传统方式获取数据
            return;
        }


        //4.使用ServletFileUpload解析器解析上传的数据
        try {
            List<FileItem>list = servletFileUpload.parseRequest(request);
            for (FileItem item:list){
                //如果fileitem是普通数据
                if (item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(name + "=" + value);

                }else {
                    String old = item.getFieldName();
                    System.out.println("oldName:"+ old);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dataStr = simpleDateFormat.format(new Date());
                    String  dataDir = dataStr.split("")[0];
                    //逻辑 先判断当前的目录  有没有 有就不创建  没有就创建
                    Path="G:/test file/"+dataDir;
                    File file1 = new File(Path);

                    if(!file1.exists()) //不存在就创建
                    {
                        file1.mkdir();
                    }
                    old = System.currentTimeMillis()+old;
                    File file0 = new File(Path+"/"+old);

                    //通过wirte写入到某个位置
                    item.write(file0);

                    message="文件上传成功！";
                    Path =Path+"/"+old;
                }

                //将图片名和路径存放在数据库
                String nameofshop = request.getParameter("nameofshop");
                con = DBUtil.getConnection();
                con.setAutoCommit(false);
                String sql = "insert into shops(name,shopUrl)values (?,?)";

                ps = con.prepareStatement(sql);
                ps.setString(1,nameofshop);
                ps.setString( 2,Path );




            }
        } catch (FileUploadException e) {
            message="文件上传失败！";
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("message",message);
        request.getRequestDispatcher("/upload.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request , HttpServletResponse response) {

    }
}


















 /*   //如果这是上传的文件
    String filename = item.getName();
                    System.out.println(filename);
                            if (filename==null||filename.trim().equals("")){
                            continue;
                            }
                            filename = filename.substring(filename.lastIndexOf("\\")+1);
                            InputStream in = item.getInputStream();//获取上传文件的输入流
                            FileOutputStream out = new FileOutputStream(savePath+"\\"+filename);//创建文件输出流
                            byte buffer[]=new byte[1024];
                            int len =0;
                            while ((len=in.read(buffer))>0){
                            out.write(buffer,0,len);
                            }
                            in.close();
                            out.close();
                            item.delete();*/
