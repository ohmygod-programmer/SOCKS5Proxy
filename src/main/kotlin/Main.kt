import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetAddress
import java.net.URL
import java.net.UnknownHostException
import java.util.*

fun main(args: Array<String>) {
    val address : InetAddress
    val port : Int
    val prop = Properties()
    try {
        val fileName = "config.properties"
        val res: URL = Objects.requireNonNull(
            SOCKS5Proxy::class.java.getResource(fileName),
            "Can't find configuration file $fileName"
        )
        val `is`: InputStream = FileInputStream(res.getFile())
        prop.load(`is`)
        try {
            address = InetAddress.getByName(prop.getProperty("ip"))
            port = prop.getProperty("port").toInt()
            if (port < 0 || port > 0xFFFF){
                println("Некорректный порт")
                return
            }
        }
        catch (e : UnknownHostException){
            println("Некорректный адрес")
            return
        }
        catch (e : java.lang.NumberFormatException){
            println("Неправильный формат порта")
            return
        }

    } catch (e: IOException) {
        e.printStackTrace()
        return
    }


    val socks5Proxy = SOCKS5Proxy(address, port)
    socks5Proxy.start()


}



