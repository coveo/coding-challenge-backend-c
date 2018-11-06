using System.Data;
using System.IO;

namespace AutoComplete.data
{
    public class DataHelper
    {
        public static DataTable LoadData(string fileName)
        {
            DataTable datatable = new DataTable();
            StreamReader streamreader = new StreamReader(fileName, System.Text.Encoding.UTF8);
            char[] delimiter = new char[] { '\t' };
            string[] columnheaders = streamreader.ReadLine().Split(delimiter);
            foreach (string columnheader in columnheaders)
            {
                System.Type columnType = typeof(string);
                switch (columnheader)
                {
                    case "id":         columnType = typeof(int); break;
                    case "lat":        columnType = typeof(double); break;
                    case "long":       columnType = typeof(double); break;
                    case "population": columnType = typeof(int); break;
                }
                datatable.Columns.Add(columnheader, columnType);
            }

            while (streamreader.Peek() > 0)
            {
                DataRow datarow = datatable.NewRow();
                datarow.ItemArray = streamreader.ReadLine().Split(delimiter);
                datatable.Rows.Add(datarow);
            }

            return datatable;
        }
    }
}
