using System;
using System.Collections.Generic;

namespace DSLib
{
    public class DataRow
    {


        private List<String> colVal;

        public DataRow(int colNum)
        {
            colVal = new List<String>();
        }

        public String this [int index]
        {
            get
            {
                return colVal[index];
            }
            set
            {
                colVal[index] = value;
            }
        }
    }
}
