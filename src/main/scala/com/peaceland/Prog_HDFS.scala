import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileUtil, Path}
import org.apache.commons.io.IOUtils
import org.apache.hadoop.fs.FileSystem
import java.io.File


// Object to get data from HDFS
object ReadHDFS extends App {

        // Initialize hadoop configuration
val hadoopconf = new Configuration()
hadoopconf.addResource(new Path("/etc/hadoop/conf/core-site.xml"))
val fs = FileSystem.get(hadoopconf);

        // Get HDFS file
        val srcPath = new Path("HDFS/report_Posted.csv")
        FileUtil.copy(
                srcPath.getFileSystem(hadoopconf),
                srcPath,
                new File("HDFS/retrievedFromHDFS.csv"),
                false,
                hadoopconf
        )
}



object WriteHDFS extends App {

val uri = "hdfs://localhost:4040"        //String url to the hadoop address
val homePath = uri+"/user/"     //home directory in my hdfs path
val conf = new Configuration()           //new configuration for hdfs
conf.set("fs.defaultFS", uri)            //setting hadoop url in the created configuration
val hdfs = FileSystem.get(conf)          //getting the hadoop file system by using the created configuration

        // Initialize hadoop configuration
val hadoopconf = new Configuration()
hadoopconf.addResource(new Path("/etc/hadoop/conf/core-site.xml"))
val fs = FileSystem.get(hadoopconf);

fs.copyFromLocalFile(new Path("data/report.csv"), new Path("HDFS/report_Posted.csv"))

}