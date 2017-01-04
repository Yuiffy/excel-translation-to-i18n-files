# excel-translation-to-i18n-files
input a xls excel table, with multi language translated in it, and input a i18n file template(such as a json), output i18n json files with multi language.

step:
1.put *.xls into ./excels dir, in the sheet1, first column is the strings will be replaced, the other columns is the replace string, first row is title. such as below:

ENG | JPS|  CHS| XXX
:---:|---|---|---
gank|ガンク|抓|呱呱
farm|ファーム|打钱|呱谷

2.put template in dir . , default name is "English823.json"

3.run

4.will get many jsons in dir ./outputFiles
such as English823_JPS.json
the "gank" in template will be replaced by "ガンク"。

(note1: if in the excel first column , stringA is another stringB's prefix,  it only can replace stringA, not do with string B. the default call use prefix " and suffix " to handle with this situation)

(note2: it do not any escape for json or for xml or others, it just see the string in excel. it's a TODO thing, in future, it should be : save normal string in excel without any escape(such as ' " ' don't need to write like ' \\" ')), then with some paramter to let the function do json escape or xml escape or other escape. if the function works, we can use one excel to do with many file formats. now we can only use one excel to do with one format, the template is json format )