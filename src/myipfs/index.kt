package myipfs

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


external val Buffer: dynamic

@JsModule("ipfs")
external val IPFS: IpfsModule

interface IpfsModule {
    fun createNode(): Ipfs
}

external interface Ipfs : Stream {
    val bitswap: dynamic
    val block: dynamic
    val bootstrap: dynamic
    val config: dynamic
    val dag: dynamic
    val dht: dynamic
    val dns: dynamic
    val files: Files
    val id: dynamic
    val init: dynamic
    val isOnline: dynamic
    val key: dynamic
    val libp2p: dynamic
    val log: dynamic
    fun ls(cid: String, callback: (err: dynamic, files: dynamic) -> Unit = definedExternally)
    val lsPullStream: (peerId: dynamic, opts: dynamic) -> dynamic
    val lsReadableStream: (cid: String, opts: dynamic) -> Stream
    val name: dynamic
    val `object`: dynamic
    val pin: dynamic
    val pingPullStream: (peerId: dynamic, opts: dynamic) -> dynamic
    val pingReadableStream: (peerId: dynamic, opts: dynamic) -> dynamic
    val preStart: (callback: dynamic) -> dynamic
    val pubsub: dynamic
    val repo: dynamic
    fun resolve()
    fun shutdown()
    fun start()
    fun state()
    fun stop()
    val stats: dynamic
    val swarm: dynamic
    val types: dynamic
    val util: dynamic
    fun version(): dynamic
}

external interface Stream {
    val on: dynamic
}

external class File {
    val depth: Int
    val hash: String
    val name: String
    val path: String
    val size: Int
    val type: String
}

external interface AddedFile {
    val path: String
    val hash: String
    val size: Int
}

external interface Files {
    suspend fun add(data: dynamic): Array<AddedFile>
    fun cat()
    fun cp()
    fun flush()
    fun get()
    fun ls()
    fun lsImmutable()
    fun mkdir()
    fun mv()
    fun read()
    fun rm()
    fun stat()
    fun write()

}

fun jsTypeOf(o: Any): String {
    return js("typeof o") as String
}

class IpfsNode {

    fun startIpfsNode() {
        val ipfs = IPFS.createNode()
        console.dir(ipfs.files)
        println(jsTypeOf("hello"))
        ipfs.on("started") {
            println("ipfs has started")
        }
        ipfs.on("ready") {
            println("ipfs is ready")
            ipfs.ls("QmQ2r6iMNpky5f1m4cnm3Yqw8VSvjuKpTcK1X7dBR1LkJF") { err: Error?, files: Array<File> ->
                println("ipfs ls")
                if (err != null) throw err
                files.forEach { file: File ->
                    console.log(file)
                }
            }
            launch {
                ipfs.files.add(Buffer.from("ABC")).forEach { console.log(it) }
            }
            val addResult = async {
                ipfs.files.add(Buffer.from("ABC"))
            }
            launch { console.log(addResult.await()) }

        }
    }
}
