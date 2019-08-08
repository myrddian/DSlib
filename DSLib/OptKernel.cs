using System.Data;
namespace DSLib
{
    public interface OptKernel
    {
        double StartVal {
            get;
        }
        string TargetVar
        {
            get;
            set;
        }
        
        double score(DataTable evalData);


    }
}