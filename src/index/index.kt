package index

import app.app
import kotlinext.js.require
import kotlinext.js.requireAll
import myipfs.IpfsNode

import react.dom.render
import kotlin.browser.document


fun main(args: Array<String>) {
    IpfsNode().startIpfsNode()
    requireAll(require.context("src", true, js("/\\.css$/")))

    render(document.getElementById("root")) {
        app()
    }

}
