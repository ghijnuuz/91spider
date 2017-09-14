## 91spider

抓取站点视频信息，并进行下载。

### 配置

#### 配置文件

```text
src/main/resources/config.properties
```

#### 配置项

- MongoUri：MongoDB Uri
- MongoDatabaseName：MongoDB数据库名
- SiteHost：站点
- SiteVideoPath：站点视频页面路径
- SiteVideoListPath：站点视频列表页面路径
- DownloadPath：视频下载目录

### 编译

```bash
./package.sh
```

### 运行

#### 抓取所有视频信息

```bash
java -jar 91spider-{version}.jar get
```

#### 下载视频

```bash
java -jar 91spider-{version}.jar download
```
