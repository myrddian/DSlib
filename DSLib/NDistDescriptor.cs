using System;
namespace DSLib
{
    public class NDistDescriptor
    {
        public NDistDescriptor()
        {
        }

        private Double mean = 0;
        private Double variance = 1;

        public Double Mean
        {
            get
            {
                return mean;
            }

            set
            {
                mean = value;
            }
        }

        public Double Variance
        {
            get
            {
                return variance;
            }

            set
            {
                variance = value;
            }
        }

    }
}
