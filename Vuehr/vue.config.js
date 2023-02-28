let proxyObj = {};
proxyObj['/'] = {
        ws: false,
        // 远程地址和端口
        target: 'http://localhost:9001',
        changeOrigin: true,
        pathRewrite: {
            '^/vhr': ''
        },
        bypass: function(req) {
            if (req.headers.accept.indexOf('html') !== -1) {
                // console.log('Skipping proxy for browser request.');
                return '/index.html';
            }
        },
    }
    // 指定远程服务器的地址
module.exports = {
    devServer: {
        // 本地地址端口
        host: 'localhost',
        port: 8081,
        proxy: proxyObj,
    },
}