using System;
using System.Collections.Generic;

namespace DSLib
{
    public class DataFrame
    {
        private List<DataRow> dataRows;
        private List<String> columNames; 

        public DataFrame()
        {
            dataRows = new List<DataRow>();
            columNames = new List<String>();
        }

        public int Columns
        {
            get
            {
                return columNames.Count;   
            }
        }

        public int Rows
        {
            get
            {
                return dataRows.Count;
            }
        }

        public DataRow this[int index]
        {
            get
            {
                return dataRows[index];
            } 
        }

        public String this[int x, int y]
        {
            get
            {
                return dataRows[x][y];
            }
        }

    }
}
