# excel-translation-to-i18n-files
这个东西，很碉。
简单来说就是有这些功能：

1.excel翻译文件、模板翻译文件作为输入，excel每行是一个字段，每列是一种语言，第一列是和模板相同的语言。生成另外各个语言的翻译文件。（实际上就是把模板里面的字段替换）
目前转义支持xml、json，其他的也能随便加。excel里面就放没转义的原文。
示例：运行com.dyf.i18n.MainExcelToOthers.main

2.能把一堆xml翻译文件汇总到一个excel里。
示例：运行com.dyf.i18n.MainXmlToExcel.main

暂时就这些功能。是java maven项目，用IDE导入项目的时候选maven项目就行，我试了eclipse和intellij都行，是不是很稳。

下面是之前写的excel转多个json的英文说明：

input a xls excel table, with multi language translated in it, and input a i18n file template(such as a json), output i18n json files with multi language.

step:
1.put *.xls into ./excels dir, in the sheet1, first column is the strings will be replaced, the other columns is the replace string, first row is title. such as below:

ENG | JPS|  CHS| XXX
:---:|---|---|---
gank|ガンク|抓|呱呱
farm|ファーム|打钱|呱谷

2.put template in dir workfiles/excel2others/templateinput . , default name is "template.json"

3.run MainExcelToOthers.main()

4.will get many jsons in dir workfiles/excel2others/outputFiles_json
such as template_JPS.json
the "gank" in template will be replaced by "ガンク"。

(note1: if in the excel first column , stringA is another stringB's prefix,  it only can replace stringA, not do with string B. the default call use prefix " and suffix " to handle with this situation)

(note2: it do not any escape for json or for xml or others, it just see the string in excel. it's a TODO thing, in future, it should be : save normal string in excel without any escape(such as ' " ' don't need to write like ' \\" ')), then with some paramter to let the function do json escape or xml escape or other escape. if the function works, we can use one excel to do with many file formats. now we can only use one excel to do with one format, the template is json format )