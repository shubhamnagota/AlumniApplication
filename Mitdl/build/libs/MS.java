import com.app.alumni.server.*;
import java.io.*;
import java.net.*;
import com.app.alumni.dl.dao.interfaces.*;
import com.app.alumni.dl.dto.interfaces.*;
import com.app.alumni.dl.dto.*;
import com.app.alumni.dl.dao.*;
import com.app.alumni.dl.exceptions.*;
import java.util.*;
import com.app.alumni.favdl.dao.*;
import com.app.alumni.favdl.dao.interfaces.*;
import com.app.alumni.favdl.dto.interfaces.*;
import com.app.alumni.favdl.dto.*;
class MS
{
public static void main(String fp[])
{
MyServer ms=new MyServer(5000);
ms.startListening();
}
}